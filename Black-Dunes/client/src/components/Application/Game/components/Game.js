import React from 'react';

import './Board.css';
import Board from './Board.js';
import TakenPiece from './TakenPiece.js';
import initialiseChessBoard from '../helpers/board-initialiser.js';
import {Container, Card, CardHeader, CardBody} from 'reactstrap'

export default class Game extends React.Component {
    constructor() {
        super();
        this.state = {
            squares: initialiseChessBoard(),
            whiteFallenSoldiers: [],
            blackFallenSoldiers: [],
            player: 1,
            sourceSelection: -1,
            status: '',
            turn: 'white'
        }
    }

    handleClick(piece, rowCol) {
        let row = parseInt((new String(rowCol)).split("-")[0]);
        let col = parseInt((new String(rowCol)).split("-")[1]);
        let i = row*12 + col;
        const squares = this.state.squares.slice();

        if (this.state.sourceSelection === -1) {
            if (!squares[i] || squares[i].player !== this.state.player) {
                //reset selection color
                for(let x = 0; x < 144; x++) {
                    squares[x].style = {
                        backgroundImage: squares[i].style['backgroundImage']
                    };
                }
                this.setState({status: "Wrong selection. Choose player " + this.state.player + " pieces."});
                squares[i] ? delete squares[i].style.backgroundColor : null;
            }
            else {
                squares[i].style = {
                    backgroundImage: squares[i].style['backgroundImage'],
                    'background-color': '#00c4ffc9'
                };
                this.setState({
                    status: "Choose destination for the selected piece",
                    sourceSelection: i
                });
            }
        }

        else if (this.state.sourceSelection > -1) {
            delete squares[this.state.sourceSelection].style.backgroundColor;
            if (squares[i] && squares[i].player === this.state.player) {
                //reset selection color
                squares[i].style = {
                    backgroundImage: squares[i].style['backgroundImage']
                };
                this.setState({
                    status: "Wrong selection. Choose valid source and destination again.",
                    sourceSelection: -1,
                });
            }
            else {

                const squares = this.state.squares.slice();
                const whiteFallenSoldiers = this.state.whiteFallenSoldiers.slice();
                const blackFallenSoldiers = this.state.blackFallenSoldiers.slice();
                const isDestEnemyOccupied = squares[i] ? true : false;
                const isMovePossible = squares[this.state.sourceSelection].isMovePossible(this.state.sourceSelection, i, isDestEnemyOccupied);
                const srcToDestPath = squares[this.state.sourceSelection].getSrcToDestPath(this.state.sourceSelection, i);
                const isMoveLegal = this.isMoveLegal(srcToDestPath);

                if(!isMovePossible)
                    console.log('impossible move');
                if(!isMoveLegal)
                    console.log('illegal move');

                if (isMovePossible && isMoveLegal) {
                    if (squares[i] !== null) {
                        if (squares[i].player === 1) {
                            whiteFallenSoldiers.push(squares[i]);
                        }
                        else {
                            blackFallenSoldiers.push(squares[i]);
                        }
                    }
                    squares[i] = squares[this.state.sourceSelection];
                    //reset selection color
                    squares[i].style = {
                        backgroundImage: squares[i].style['backgroundImage']
                    };
                    squares[this.state.sourceSelection] = null;
                    let player = this.state.player === 1 ? 2 : 1;
                    let turn = this.state.turn === 'white' ? 'black' : 'white';
                    this.setState({
                        sourceSelection: -1,
                        squares: squares,
                        whiteFallenSoldiers: whiteFallenSoldiers,
                        blackFallenSoldiers: blackFallenSoldiers,
                        player: player,
                        status: '',
                        turn: turn
                    });
                }
                else {
                    console.log("here");
                    const squares = this.state.squares.slice();
                    console.log(squares);
                    //reset selection color
                    for(let x = 0; x < 144; x++) {
                        try{
                            squares[x].style = {
                                backgroundImage: squares[x].style['backgroundImage']
                            };
                        }
                        catch(err) { }
                    }
                    this.setState({
                        status: "Wrong selection. Choose valid source and destination again.",
                        sourceSelection: -1,
                    });

                }
            }
        }

    }

    /**
     * Check all path indices are null. For one steps move of pawn/others or jumping moves of knight array is empty, so  move is legal.
     * @param  {[type]}  srcToDestPath [array of board indices comprising path between src and dest ]
     * @return {Boolean}
     */
    isMoveLegal(srcToDestPath) {
        let isLegal = true;
        for (let i = 0; i < srcToDestPath.length; i++) {
            if (this.state.squares[srcToDestPath[i]] !== null) {
                isLegal = false;
            }
        }
        return isLegal;
    }

    render() {
        return (
            <Container>
                <Card>
                    <CardBody>
                        <div>
                            <div className="game row">
                                <div className="game-board col-lg-8 col-sm-12">
                                    <Board
                                        squares={this.state.squares}
                                        onClick={(i, j) => this.handleClick(i, j)}
                                    />
                                </div>
                                <div className="game-info col-lg-4 col-sm-12">
                                    <h3>Turn</h3>
                                    <div id="player-turn-box" style={{backgroundColor: this.state.turn}}>

                                    </div>
                                    <div className="game-status">{this.state.status}</div>

                                    <div className="fallen-soldier-block">

                                        {<TakenPiece
                                            whiteFallenSoldiers={this.state.whiteFallenSoldiers}
                                            blackFallenSoldiers={this.state.blackFallenSoldiers}
                                        />
                                        }
                                    </div>

                                </div>
                            </div>
                        </div>
                    </CardBody>
                </Card>
            </Container>

        );
    }
}

