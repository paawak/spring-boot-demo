import React, { FunctionComponent } from 'react';
import { bind } from "@react-rxjs/core"
import { createSignal } from "@react-rxjs/utils"
import { map } from "rxjs/operators"

type CustomerListProps = {
}

const [startClicked$, setStart] = createSignal<string>();

const [doStarted, started$] = bind(startClicked$, "")

const [useCharCount] = bind(
    started$.pipe(
      map((text) => text.length)
    )
  )


export const CustomerList: FunctionComponent<CustomerListProps> = ({ }) => {

    return (
        <div>
                <div>
                    <br />
                    Echo: {useCharCount()}
                </div>
            <div>
                <div>
                    <h2>Customer List</h2>
                    <a href="#" id="fetch"
                    onClick={(e) => setStart("DummyText")}
                    >Fetch</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="#" id="stop">Stop</a>
                </div>
            </div>

            <div className="div-table" id="tableDiv">
                <div className="div-table-row">
                    <div className="div-table-col">Customer ID</div>
                    <div className="div-table-col">Customer Age</div>
                    <div className="div-table-col">Customer Job</div>
                </div>
            </div>
        </div>
    );
}
