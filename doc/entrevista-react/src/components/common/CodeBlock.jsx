import './CodeBlock.css';

export function CodeBlock({ code, language = 'java' }) {
    if (!code) return null;

    const copyToClipboard = async () => {
        try {
            await navigator.clipboard.writeText(code);
            // Could add a toast notification here
        } catch (err) {
            console.error('Failed to copy:', err);
        }
    };

    return (
        <div className="code-block">
            <div className="code-header">
                <span className="code-language">{language}</span>
                <button className="copy-btn" onClick={copyToClipboard} title="Copiar código">
                    📋
                </button>
            </div>
            <pre>
                <code className={`language-${language}`}>{code}</code>
            </pre>
        </div>
    );
}
