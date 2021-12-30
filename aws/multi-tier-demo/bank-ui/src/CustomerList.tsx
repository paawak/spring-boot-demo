import { FunctionComponent, useState, useEffect } from 'react';
import { CustomerDetails } from './CustomerDetails';
import { CustomerDetailsProps } from './CustomerDetailsProps';

type CustomerListProps = {
    url: string
}

export const CustomerList: FunctionComponent<CustomerListProps> = ({ url }) => {

    const [customers, setCustomers] = useState<CustomerDetailsProps[]>([]);
    const [error, setError] = useState<boolean>(false);

    useEffect(() => {
        fetch(url)
          .then(response => response.json())
          .then(response => setCustomers(response))
          .catch(error => {
              setError(true);
              console.error("Error fetching customer list", error);
            });
      }, []); 

    const customerRows = customers.map((customer)=>
      <CustomerDetails id={customer.id} age={customer.age} job={customer.job}/>
    );

    return (
        <div>
            <div>
                <div>
                    <h2>Customer List</h2>                    
                </div>
            </div>

            <div className="div-table" id="tableDiv">
                <div className="div-table-row">
                    <div className="div-table-col">Customer ID</div>
                    <div className="div-table-col">Customer Age</div>
                    <div className="div-table-col">Customer Job</div>
                </div>
                {customerRows}
            </div>
        </div>
    );
}
