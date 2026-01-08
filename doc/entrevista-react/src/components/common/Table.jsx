import './Table.css';

export function Table({ headers, rows }) {
    if (!headers || !rows) return null;

    return (
        <div className="table-wrapper">
            <table className="styled-table">
                <thead>
                    <tr>
                        {headers.map((header, i) => (
                            <th key={i}>{header}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {rows.map((row, i) => (
                        <tr key={i}>
                            {row.map((cell, j) => (
                                <td key={j} dangerouslySetInnerHTML={{ __html: cell }} />
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
