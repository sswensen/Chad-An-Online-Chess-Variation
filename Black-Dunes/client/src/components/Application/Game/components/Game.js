import React from 'react';

import './Board.css';
import Board from './Board.js';
import TakenPiece from './TakenPiece.js';
import initialiseChessBoard from '../helpers/board-initialiser.js';
import {Container, Card, CardHeader, CardBody} from 'reactstrap';

import {get_config, request} from '../../../../api/api';
import Rook from '../pieces/rook.js';
import King from '../pieces/king.js';
import Queen from '../pieces/queen.js';
import piece from "../pieces/piece";

export default class Game extends React.Component {
    constructor() {
        super();
        this.state = {
            userID: 0,
            squares: initialiseChessBoard(),
            whiteFallenSoldiers: [],
            blackFallenSoldiers: [],
            player: 1,
            sourceSelection: -1,
            status: '',
            turn: 'white',
            games: [],
            selectedGame: 0
        };
        this.updateUserID = this.updateUserID.bind(this);
        this.getGames();
    }

    updateUserID(id) {

        this.setState({
           userID: id
        });
        console.log("userID: " + this.userID);
    }

    convertBoard() {
        const squares = this.state.squares.slice();
        let boardString = "";
        for(let i = 0, j = 0; i < 144; i++, j++) {
            if(squares[i]) {
                // white
                if(squares[i].player == 1) {
                    if(squares[i].constructor.name == "Rook") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 1 + "," + 1);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 1 + "," + 1 + " "
                    }
                    if(squares[i].constructor.name == "Queen") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 2 + "," + 1);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 2 + "," + 1 + " "
                    }
                    if(squares[i].constructor.name == "King") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 3 + "," + 1);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 3 + "," + 1 + " "
                    }
                } else {
                // black
                    if(squares[i].constructor.name == "Rook") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 1 + "," + 0);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 1 + "," + 0 + " "
                    }
                    if(squares[i].constructor.name == "Queen") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 2 + "," + 0);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 2 + "," + 0 + " "
                    }
                    if(squares[i].constructor.name == "King") {
                        // console.log(Math.floor(j/12) + "," + i%12 + "," + 3 + "," + 0);
                        boardString += Math.floor(j/12) + "," + i%12 + "," + 3 + "," + 0 + " "
                    }

                }
            }
        }
        boardString = boardString.trim();
        return boardString;
    }

    rebuildBoard(boardString) {
        // new board
        const squares = Array(144).fill(null);

        let splitArray = boardString.split(" ");
        for(let i = 0; i < splitArray.length; i++) {
            console.log(splitArray[i]);
            let pieceInfo = splitArray[i].split(",");
            let row = parseInt(pieceInfo[0]);
            // console.log(row);
            let col = parseInt(pieceInfo[1]);
            // console.log(col);
            let id = (row * 12) + col;
            console.log(id);
            // piecetype 1=rook 2=queen 3=king
            let pieceType = pieceInfo[2];

            // piece color 0=black 1=white
            let pieceColor = pieceInfo[3];

            if(pieceType == 1) {
                if (pieceColor == 0) {
                    // black
                    squares[id] = new Rook(2);
                } else {
                    // white
                    squares[id] = new Rook(1);
                }
            }
            if (pieceType == 2) {
                if (pieceColor == 0) {
                    // black
                    squares[id] = new Queen(2);
                } else {
                    // white
                    squares[id] = new Queen(1);
                }
            }
            if (pieceType == 3) {
                if (pieceColor == 0) {
                    // black
                    squares[id] = new King(2);
                } else {
                    // white
                    squares[id] = new King(1);
                }
            }
        }
        return squares;
    }

    arraysEqual(arr1, arr2) {
        if(arr1.length !== arr2.length)
            return false;
        for(var i = arr1.length; i--;) {
            if(arr1[i] !== arr2[i]) {
                console.log(arr1[i]);
                console.log(arr2[i]);
                return false;
            }
        }

        return true;
    }

    handleClick(piece, rowCol) {

        let row = parseInt((new String(rowCol)).split("-")[0]);
        let col = parseInt((new String(rowCol)).split("-")[1]);
        let i = row*12 + col;
        const squares = this.state.squares.slice();

        // testing conversion functions
        console.log(this.convertBoard())
        let newboard = this.rebuildBoard(this.convertBoard());
        console.log(newboard.length);
        console.log(this.state.squares.length);
        console.log(this.arraysEqual(newboard, this.state.squares));

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
                //Add api call here
                // let moves = [[6,4],[4,7]];
                // this.highlightValidMoves(moves);
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
                    const squares = this.state.squares.slice();
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
            this.clearHighlights();
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

    highlightValidMoves(moves) {
        for(let i = 0; i < moves.length; i++) {
            console.log(document.getElementById(moves[i][0] + '-' + moves[i][1]).style.backgroundImage);
            if(document.getElementById(moves[i][0] + '-' + moves[i][1]).style.backgroundImage === "")
                document.getElementById(moves[i][0] + '-' + moves[i][1]).style.backgroundColor = "#00c4ffc9";
            else
                document.getElementById(moves[i][0] + '-' + moves[i][1]).style.backgroundColor = "#ff4936c9";
        }

    }

    clearHighlights() {
        for(let i = 0; i < 12; i++) {
            for(let j = 0; j < 12; j++) {
                if(!this.isWall(i, j))
                    document.getElementById(i + '-' + j).style.backgroundColor = "#ffce9e";
                else
                    document.getElementById(i + '-' + j).style.backgroundColor = "#d18b47";
            }
        }
    }

    isWall(row, col) {

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

    getGames() {
        let update = request(this.state.userID, 'getGames');
        update.then((value => {
            this.updateGames(value);
            console.log(value);
        }));
    }

    updateGames(value) {
        this.setState({
            games: value
        });
    }

    render() {
        return (
            <Container>
                <Card>
                    <CardBody>
                        <div>
                            <h3>Games</h3>
                            {this.state.games}
                        </div>
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

