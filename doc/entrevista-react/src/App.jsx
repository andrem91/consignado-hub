import { useState, useMemo, useEffect } from 'react';
import { Sidebar } from './components/layout/Sidebar';
import { Header } from './components/layout/Header';
import { Section } from './components/sections/Section';
import { SearchBox } from './components/common/SearchBox';
import { useSearch } from './hooks/useSearch';
import questionsData from './data/questions.json';
import './App.css';

function App() {
  const [activeSection, setActiveSection] = useState(questionsData[0]?.id || '');

  // Search functionality
  const { query, setQuery, results, totalResults, isSearching } = useSearch(
    questionsData,
    ['title', 'content', 'answer']
  );

  // Data to display (filtered or all)
  const displayData = isSearching ? results : questionsData;

  // Calculate stats from original data
  const stats = useMemo(() => {
    const questionCount = questionsData.reduce(
      (acc, section) => acc + section.questions.length,
      0
    );
    return {
      questionCount,
      sectionCount: questionsData.length
    };
  }, []);

  // Keyboard shortcut (Ctrl+K)
  useEffect(() => {
    const handleKeyDown = (e) => {
      if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
        e.preventDefault();
        document.querySelector('.search-input')?.focus();
      }
      if (e.key === 'Escape') {
        setQuery('');
      }
    };
    document.addEventListener('keydown', handleKeyDown);
    return () => document.removeEventListener('keydown', handleKeyDown);
  }, [setQuery]);

  // Handle section click
  const handleSectionClick = (sectionId) => {
    setActiveSection(sectionId);
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  };

  return (
    <div className="app">
      <Sidebar
        sections={questionsData}
        activeSection={activeSection}
        onSectionClick={handleSectionClick}
      />

      <main className="content">
        <header className="hero">
          <h1>🎯 Guia de Entrevista</h1>
          <p className="subtitle">Java Pleno/Sênior • {stats.questionCount} perguntas com respostas detalhadas</p>

          <div className="stats">
            <div className="stat">
              <span className="stat-number">{stats.questionCount}</span>
              <span className="stat-label">Perguntas</span>
            </div>
            <div className="stat">
              <span className="stat-number">{stats.sectionCount}</span>
              <span className="stat-label">Categorias</span>
            </div>
            <div className="stat">
              <span className="stat-number">78</span>
              <span className="stat-label">Testes no Projeto</span>
            </div>
          </div>

          <SearchBox
            value={query}
            onChange={setQuery}
            totalResults={totalResults}
            isSearching={isSearching}
          />
        </header>

        {isSearching && displayData.length === 0 && (
          <div className="no-results">
            <p>😕 Nenhuma pergunta encontrada para "{query}"</p>
            <button onClick={() => setQuery('')}>Limpar busca</button>
          </div>
        )}

        {displayData.map(section => (
          <Section key={section.id} section={section} />
        ))}

        <footer className="footer">
          <p>🍀 Boa sorte na entrevista!</p>
          <p className="footer-tip">
            Dica: Quando não souber, diga "Não tenho experiência prática, mas pelo que entendo..."
          </p>
          <p className="footer-shortcut">
            Atalhos: <kbd>Ctrl+K</kbd> Buscar • <kbd>Esc</kbd> Limpar
          </p>
        </footer>
      </main>
    </div>
  );
}

export default App;
