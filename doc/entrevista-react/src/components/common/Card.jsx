import { useState } from 'react';
import './Card.css';

export function Card({ title, children, tags = [], answer }) {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className={`card ${isOpen ? 'card--open' : ''}`}>
            <div className="card-header" onClick={() => setIsOpen(!isOpen)}>
                <div className="card-header-content">
                    <h3 className="card-title">{title}</h3>
                    {tags.length > 0 && (
                        <div className="card-tags">
                            {tags.map(tag => (
                                <span
                                    key={tag}
                                    className={`tag tag--${tag.replace(/\s/g, '-')}`}
                                >
                                    {tag}
                                </span>
                            ))}
                        </div>
                    )}
                </div>
                <span className="toggle-icon">{isOpen ? '−' : '+'}</span>
            </div>

            {isOpen && (
                <div className="card-content">
                    {children}

                    {answer && (
                        <div className="interview-answer">
                            <strong>🎤 Resposta para entrevista:</strong>
                            <p>"{answer}"</p>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}
