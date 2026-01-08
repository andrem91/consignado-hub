const fs = require('fs');
const path = require('path');

// Read the markdown file - go up from src/data to doc/
const mdPath = path.join(__dirname, '..', '..', '..', 'PERGUNTAS_ENTREVISTA.md');
const mdContent = fs.readFileSync(mdPath, 'utf-8');

// Parse sections and questions
function parseMarkdown(md) {
    const sections = [];
    const lines = md.split('\n');

    let currentSection = null;
    let currentQuestion = null;
    let contentBuffer = [];
    let inCodeBlock = false;
    let codeLanguage = '';
    let codeBuffer = [];

    for (let i = 0; i < lines.length; i++) {
        const line = lines[i].replace(/\r$/, '');

        // Track code blocks
        if (line.startsWith('```')) {
            if (!inCodeBlock) {
                inCodeBlock = true;
                codeLanguage = line.slice(3).trim() || 'java';
                codeBuffer = [];
            } else {
                inCodeBlock = false;
                if (currentQuestion) {
                    currentQuestion.code = codeBuffer.join('\n');
                }
            }
            continue;
        }

        if (inCodeBlock) {
            codeBuffer.push(line);
            continue;
        }

        // Section header (## )
        if (line.startsWith('## ')) {
            // Save previous question
            if (currentQuestion && contentBuffer.length > 0) {
                currentQuestion.content = cleanContent(contentBuffer.join('\n'));
            }

            const title = line.slice(3).trim();
            // Extract section number from title
            const numMatch = title.match(/(\d+)\./);
            const sectionNum = numMatch ? numMatch[1] : (sections.length + 1);

            // Generate clean ID based on section number
            const id = `section-${sectionNum}`;

            currentSection = {
                id,
                title,
                questions: []
            };
            sections.push(currentSection);
            currentQuestion = null;
            contentBuffer = [];
            continue;
        }

        // Question header (### )
        if (line.startsWith('### ')) {
            // Save previous question
            if (currentQuestion && contentBuffer.length > 0) {
                currentQuestion.content = cleanContent(contentBuffer.join('\n'));
            }

            const title = line.slice(4).trim();
            const id = `q-${sections.length}-${currentSection?.questions.length || 0}`;

            currentQuestion = {
                id,
                title,
                tags: inferTags(title),
                content: ''
            };

            if (currentSection) {
                currentSection.questions.push(currentQuestion);
            }
            contentBuffer = [];
            continue;
        }

        // Skip dividers
        if (line.startsWith('---')) {
            continue;
        }

        // Collect content
        if (currentQuestion && line.trim()) {
            // Extract tables
            if (line.startsWith('|') && !currentQuestion.table) {
                const tableLines = [];
                let j = i;
                while (j < lines.length && lines[j].startsWith('|')) {
                    tableLines.push(lines[j]);
                    j++;
                }
                if (tableLines.length >= 3) {
                    currentQuestion.table = parseTable(tableLines);
                    i = j - 1;
                    continue;
                }
            }

            // Extract tips
            if (line.includes('No ConsignadoHub') || line.includes('No meu projeto')) {
                const tipMatch = line.match(/\*?"?(.+)"?\*?$/);
                if (tipMatch) {
                    currentQuestion.tip = tipMatch[1].replace(/\*+/g, '').replace(/"/g, '');
                }
            }

            // Extract interview answers
            if (line.includes('Resposta para entrevista')) {
                const nextLine = lines[i + 1]?.replace(/\r$/, '');
                if (nextLine) {
                    currentQuestion.answer = nextLine.replace(/^\*"?|"?\*$/g, '').replace(/"/g, '');
                    i++;
                    continue;
                }
            }

            contentBuffer.push(line);
        }
    }

    // Save last question
    if (currentQuestion && contentBuffer.length > 0) {
        currentQuestion.content = cleanContent(contentBuffer.join('\n'));
    }

    return sections.filter(s => s.questions.length > 0);
}

function parseTable(lines) {
    const headers = lines[0].split('|').filter(c => c.trim()).map(c => c.trim());
    const rows = lines.slice(2).map(line =>
        line.split('|').filter(c => c.trim()).map(c => c.trim())
    );
    return { headers, rows };
}

function cleanContent(content) {
    return content
        .replace(/\*\*Resposta:\*\*/g, '')
        .replace(/\n{3,}/g, '\n\n')
        .trim()
        .slice(0, 2000); // Limit content length
}

function inferTags(title) {
    const tags = [];
    const lower = title.toLowerCase();

    // Difficulty
    if (lower.includes('o que é') || lower.includes('explique') || lower.includes('diferença')) {
        tags.push('básico');
    } else if (lower.includes('quando') || lower.includes('como') || lower.includes('resolver')) {
        tags.push('intermediário');
    } else if (lower.includes('virtual') || lower.includes('locking') || lower.includes('saga')) {
        tags.push('avançado');
    }

    // Importance
    if (lower.includes('solid') || lower.includes('acid') || lower.includes('n+1') ||
        lower.includes('transactional') || lower.includes('ioc') || lower.includes('tdd')) {
        tags.push('importante');
    }

    return tags.length > 0 ? tags : ['intermediário'];
}

// Parse and save
const sections = parseMarkdown(mdContent);

// Stats
let totalQuestions = 0;
sections.forEach(s => {
    totalQuestions += s.questions.length;
});

// Write output
const outputPath = path.join(__dirname, 'questions.json');
fs.writeFileSync(outputPath, JSON.stringify(sections, null, 2));

console.log(`✅ Conversão concluída!`);
console.log(`   📊 ${totalQuestions} perguntas`);
console.log(`   📁 ${sections.length} seções`);
console.log(`   💾 Salvo em: ${outputPath}`);
