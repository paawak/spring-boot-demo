import React, { FunctionComponent } from 'react';
import { CustomerDetailsProps } from './CustomerDetailsProps';

export const CustomerDetails: FunctionComponent<CustomerDetailsProps> = ({ id, age, job }) => {
    return (
        <div className="div-table-row" key={id}> 
            <div className="div-table-col">{id}</div>
            <div className="div-table-col">{age}</div>
            <div className="div-table-col">{job}</div>
        </div>
    );
}
