import { CustomerList } from './CustomerList';
import './App.css';

function App() {
  return (
      <CustomerList url='http://localhost:8080/bank-item/blocking'/>
  );
}

export default App;
