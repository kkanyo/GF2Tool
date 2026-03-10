import { Link } from 'react-router-dom';

function Navigation() {
  return (
    <nav>
      <Link to="dolls/list">인형 목록</Link>
      <Link to="dolls/register">인형 등록</Link>
    </nav>
  );
}

export { Navigation };
