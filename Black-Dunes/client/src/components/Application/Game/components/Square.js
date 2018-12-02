import React from 'react';

import './Board.css';

export default function Square(props) {

    return (
        <button className={"square " + props.shade}
                onClick={props.onClick}
                style={props.style}
                id={props.id}>

        </button>
    );

}
