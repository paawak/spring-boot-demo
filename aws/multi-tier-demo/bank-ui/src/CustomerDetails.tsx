import React, { FunctionComponent } from 'react';

type CustomerDetailsProps = {
    id: string,
    age: string,
    job: string
}

export const CustomerDetails: FunctionComponent<CustomerDetailsProps> = ({ id, age, job }) => {
    return (
        <div className="div-table-row"> 
            <div className="div-table-col">{id}</div>
            <div className="div-table-col">{age}</div>
            <div className="div-table-col">{job}</div>
        </div>
    );
}
