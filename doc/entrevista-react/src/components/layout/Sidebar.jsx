import './Sidebar.css';

export function Sidebar({ sections, activeSection, onSectionClick }) {
    return (
        <nav className="sidebar">
            <div className="sidebar-header">
                <h2>📚 Tópicos</h2>
            </div>

            <ul className="nav-list">
                {sections.map(section => (
                    <li key={section.id}>
                        <a
                            href={`#${section.id}`}
                            className={`nav-link ${activeSection === section.id ? 'active' : ''}`}
                            onClick={(e) => {
                                e.preventDefault();
                                onSectionClick(section.id);
                            }}
                        >
                            {section.title.replace(/^\d+\.\s*/, '').substring(0, 25)}...
                        </a>
                    </li>
                ))}
            </ul>

            <div className="sidebar-footer">
                <button onClick={() => window.dispatchEvent(new CustomEvent('expandAll'))}>
                    📖 Expandir Tudo
                </button>
                <button onClick={() => window.dispatchEvent(new CustomEvent('collapseAll'))}>
                    📕 Fechar Tudo
                </button>
            </div>
        </nav>
    );
}
