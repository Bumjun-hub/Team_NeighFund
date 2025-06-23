import dummydata from "../../datas/dummydata";

// SuggestionPage.js
import React, { useState } from 'react';
import './SuggestionPage.css'; // 스타일 분리

const SuggestionPage = () => {
    const [categoryFilter, setCategoryFilter] = useState('전체');
    const [sortType, setSortType] = useState('최신순');

    const filtered = dummydata
        .filter((item) =>
            categoryFilter === '전체' ? true : item.category === categoryFilter
        )
        .sort((a, b) => {
            if (sortType === '공감순') return b.likes - a.likes;
            return new Date(b.date) - new Date(a.date); // 최신순
        });

    return (
        <div className="suggestion-wrapper">
            <div className="suggestion-header">
                <div className="suggestion-title">
                    <h2>제안</h2>
                </div>
                <div className="filters">
                    <select value={sortType} onChange={(e) => setSortType(e.target.value)}>
                        <option value="최신순">최신순</option>
                        <option value="공감순">공감순</option>
                    </select>
                    <select value={categoryFilter} onChange={(e) => setCategoryFilter(e.target.value)}>
                        <option value="전체">전체</option>
                        <option value="환경">환경</option>
                        <option value="교육">교육</option>
                    </select>
                </div>
            </div>

            <div className="suggestion-list">
                {filtered.map((item) => (
                    <div key={item.id} className="suggestion-card" data-category={item.category}>
                        <div className="category">#{item.category}</div>
                        <div className="title">{item.title}</div>
                        <div className="content">{item.content}</div>
                        <div className="meta">
                            <span>♡ {item.likes}</span>
                            <span>{item.date}</span>
                            <span className="status">{item.status}</span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SuggestionPage;
