import { useState, useMemo } from 'react';

export function useSearch(data, searchKeys = ['title', 'content']) {
    const [query, setQuery] = useState('');

    const results = useMemo(() => {
        if (!query.trim()) {
            return data;
        }

        const lowerQuery = query.toLowerCase();

        return data.map(section => {
            const filteredQuestions = section.questions.filter(question =>
                searchKeys.some(key => {
                    const value = question[key];
                    return value && value.toLowerCase().includes(lowerQuery);
                })
            );

            if (filteredQuestions.length > 0) {
                return {
                    ...section,
                    questions: filteredQuestions
                };
            }
            return null;
        }).filter(Boolean);
    }, [data, query, searchKeys]);

    const totalResults = useMemo(() =>
        results.reduce((acc, section) => acc + section.questions.length, 0),
        [results]
    );

    return {
        query,
        setQuery,
        results,
        totalResults,
        isSearching: query.trim().length > 0
    };
}
