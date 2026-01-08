import './Header.css';

export function Header({ questionCount, sectionCount }) {
    return (
        <header className="hero">
            <h1>🎯 Guia de Entrevista</h1>
            <p className="subtitle">Java Pleno/Sênior • {questionCount} perguntas com respostas detalhadas</p>

            <div className="stats">
                <div className="stat">
                    <span className="stat-number">{questionCount}</span>
                    <span className="stat-label">Perguntas</span>
                </div>
                <div className="stat">
                    <span className="stat-number">{sectionCount}</span>
                    <span className="stat-label">Categorias</span>
                </div>
                <div className="stat">
                    <span className="stat-number">78</span>
                    <span className="stat-label">Testes no Projeto</span>
                </div>
            </div>

            <div className="search-container">
                <input
                    type="text"
                    placeholder="🔍 Buscar pergunta... (Ctrl+K)"
                    className="search-input"
                    onFocus={(e) => e.target.select()}
                />
            </div>
        </header>
    );
}
