import { Card } from '../common/Card';
import { Table } from '../common/Table';
import { CodeBlock } from '../common/CodeBlock';
import { Tip } from '../common/Tip';
import './QuestionCard.css';

export function QuestionCard({ question }) {
    return (
        <Card
            title={question.title}
            tags={question.tags}
            answer={question.answer}
        >
            {/* Content as markdown-like text */}
            <div
                className="question-content"
                dangerouslySetInnerHTML={{
                    __html: formatContent(question.content)
                }}
            />

            {/* Table if exists */}
            {question.table && (
                <Table
                    headers={question.table.headers}
                    rows={question.table.rows}
                />
            )}

            {/* Code block if exists */}
            {question.code && (
                <CodeBlock code={question.code} />
            )}

            {/* Tip if exists */}
            {question.tip && (
                <Tip type="project">
                    <strong>No ConsignadoHub:</strong> {question.tip}
                </Tip>
            )}
        </Card>
    );
}

// Simple markdown-like formatting
function formatContent(content) {
    if (!content) return '';

    return content
        // Bold
        .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
        // Inline code
        .replace(/`([^`]+)`/g, '<code>$1</code>')
        // Line breaks
        .replace(/\n\n/g, '</p><p>')
        .replace(/\n/g, '<br>')
        // Wrap in paragraphs
        .replace(/^(.+)$/, '<p>$1</p>');
}
