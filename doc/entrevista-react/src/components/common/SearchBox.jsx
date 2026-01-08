import './SearchBox.css';

export function SearchBox({ value, onChange, totalResults, isSearching }) {
    return (
        <div className="search-box-container">
            <input
                type="text"
                placeholder="🔍 Buscar pergunta... (Ctrl+K)"
                value={value}
                onChange={(e) => onChange(e.target.value)}
                className="search-input"
            />
            {isSearching && (
                <div className="search-results-count">
                    {totalResults} {totalResults === 1 ? 'resultado' : 'resultados'}
                </div>
            )}
            {value && (
                <button
                    className="search-clear"
                    onClick={() => onChange('')}
                    title="Limpar busca"
                >
                    ✕
                </button>
            )}
        </div>
    );
}
