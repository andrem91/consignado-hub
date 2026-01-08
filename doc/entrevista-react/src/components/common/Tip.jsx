import './Tip.css';

export function Tip({ children, type = 'info' }) {
    const icons = {
        info: '💡',
        warning: '⚠️',
        success: '✅',
        project: '🎯'
    };

    return (
        <div className={`tip tip--${type}`}>
            <span className="tip-icon">{icons[type]}</span>
            <div className="tip-content">{children}</div>
        </div>
    );
}
