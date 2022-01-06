import { CustomerList } from './CustomerList';
import './App.css';
import { Config } from './Config';

function App() {

  return (
      <CustomerList url={Config.REST_API_BASE_NAME + "/bank-item/blocking"}/>
  );
}

export default App;
