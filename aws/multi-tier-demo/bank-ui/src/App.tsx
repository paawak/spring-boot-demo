import { Subscribe } from "@react-rxjs/core"
import { CustomerList } from './CustomerList';
import './App.css';

function App() {
  return (
  <>
    <Subscribe>
      <CustomerList/>
    </Subscribe>
  </>
  );
}

export default App;
