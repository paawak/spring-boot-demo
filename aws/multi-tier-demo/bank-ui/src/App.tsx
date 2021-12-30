import { CustomerList } from './CustomerList';
import './App.css';

function App() {
  const url = `http://${process.env.REACT_APP_REST_API_BASE_NAME}/bank-item/blocking`
  return (
      <CustomerList url={url}/>
  );
}

export default App;
