import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import '../styles/App.css';
import MyPage from './Mypage/MyPage';

// 페이지 컴포넌트들 타입 정의
type HomeProps = {
  title?: string;
}

const Home: React.FC<HomeProps> = ({ title = "Welcome to React" }) => {
  return (
    <div className="App">
      <header className="App-header">
        <p>{title}</p>
        <p className='text-sm'>TailWind 확인용</p>
        <Link to="/about" className="App-link">
          Go to About
        </Link>
        <Link to="/mypage" className="App-link">
          Go to MyPage
        </Link>
      </header>
    </div>
  );
};

const About: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <h1>About Page</h1>
        <Link
          to="/"
          className="App-link"
        >
          Go to Home
        </Link>
      </header>
    </div>
  );
};

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </Router>
  );
};

export default App;