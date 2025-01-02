import React from 'react';
import { Link } from 'react-router-dom';

interface MyPageProps {
    // props가 필요한 경우 여기에 추가
}

const MyPage: React.FC<MyPageProps> = () => {
    return (
        <div>
            <div className="">
                <h1>마이페이지</h1>
                <Link to="/" className="App-link"> Go to Home</Link>
            </div>
        </div>
    );
};

export default MyPage;