import React from 'react';

import './Board.css';
import Square from './Square.js';

export default class Board extends React.Component {

    renderSquare(i, squareShade, row) {
        return <Square
            id={row + '-' + i % 12}
            style={this.props.squares[i] ? this.props.squares[i].style : null}
            shade={squareShade}
            onClick={() => this.props.onClick(this.props.squares[i], row + '-' + i % 12)}
        />
    }

    render() {
        const board = [];
        for (let i = 0; i < 12; i++) {
            const squareRows = [];
            for (let j = 0; j < 12; j++) {
                if(isWall(i,j)) {
                    const squareShade = "wall-square";
                    squareRows.push(this.renderSquare((i * 12) + j, squareShade, i));
                }

                else {
                    const squareShade = "square-color";
                    squareRows.push(this.renderSquare((i * 12) + j, squareShade, i));
                }
            }
            board.push(<div id={i} className="board-row">{squareRows}</div>)
        }

        return (
            <div className="board">
                {board}
            </div>
        );
    }
}


function isEven(num) {
    return num % 2 === 0
}

function isWall(row, col) {

    // BLACK walls
    if(row == 1 && col >= 7 && col <= 9)
        return true;
    if(col == 6 && row >= 2 && row <= 4)
        return true;
    if(col == 10 && row >= 2 && row <= 4)
        return true;
    if(row == 5 && col >= 7 && col <= 9)
        return true;

    // WHITE walls
    if(row == 6 && col >= 2 && col <= 4)
        return true;
    if(col == 1 && row >= 7 && row <= 9)
        return true;
    if(col == 5 && row >= 7 && row <= 9)
        return true;
    if(row == 10 && col >= 2 && col <= 4)
        return true;

    // If not a wall return false
    return false;
}