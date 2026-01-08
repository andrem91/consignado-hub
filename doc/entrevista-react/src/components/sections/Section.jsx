import { QuestionCard } from './QuestionCard';
import './Section.css';

export function Section({ section }) {
    return (
        <section id={section.id} className="section">
            <h2 className="section-title">{section.title}</h2>

            <div className="questions-list">
                {section.questions.map(question => (
                    <QuestionCard key={question.id} question={question} />
                ))}
            </div>
        </section>
    );
}
