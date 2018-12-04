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
import Select from "react-select";

export default class Game extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            squares: [],
            whiteFallenSoldiers: [],
            blackFallenSoldiers: [],
            player: -1,
            sourceSelection: -1,
            status: '',
            turn: 'white',
            games: [],
            gameID: 1,
            validMoves: []
        };
        this.getGames();
        this.getBoard();
    }


    getGames() {
        let user = {
            userID: this.props.userID
        }
        let update = request(user, 'getGames');
        update.then((value => {
            this.updateGames(value);
        }));
    }

    updateGames(value) {
        this.setState({
            games: this.getGamesDisplay(value)
        });
    }

    getBoard() {
        // console.log("getBoard() gameID:" + this.state.gameID);
        let gameInfo = {
            gameID: this.state.gameID
        };
        let update = request(gameInfo, 'getBoard');
        update.then((value => {
            this.updateGame(value);
        }));
    }

    updateGame(value) {
        this.setState({
            squares: this.rebuildBoard(value["board"]),
            turn: value["turn"] == '0' ? 'white' : 'black',
            player: value["userID"] == this.props.userID ? 1 : 2
        });
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
            // console.log(splitArray[i]);
            let pieceInfo = splitArray[i].split(",");
            let row = parseInt(pieceInfo[0]);
            // console.log(row);
            let col = parseInt(pieceInfo[1]);
            // console.log(col);
            let id = (row * 12) + col;
            // console.log(id);
            // piecetype 1=rook 2=queen 3=king
            let pieceType = pieceInfo[2];
            // piece color 0=black 1=white
            let pieceColor = pieceInfo[3];

            if(pieceType === '1') {
                if (pieceColor === '0') {
                    // black
                    squares[id] = new Rook(2);
                } else {
                    // white
                    squares[id] = new Rook(1);
                }
            }
            if (pieceType === '2') {
                if (pieceColor === '0') {
                    // black
                    squares[id] = new Queen(2);
                } else {
                    // white
                    squares[id] = new Queen(1);
                }
            }
            if (pieceType === '3') {
                if (pieceColor === '0') {
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
        let newboard = this.rebuildBoard(this.convertBoard());
        // console.log(newboard.length);
        // console.log(this.state.squares.length);
        // console.log(this.arraysEqual(newboard, this.state.squares));

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
                this.getValidMoves(row, col);
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


                if (this.isValidMove(row, col)) {
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

                    this.makeMove(row, col, this.state.sourceSelection);
                    this.setState({
                        sourceSelection: -1,
                        whiteFallenSoldiers: whiteFallenSoldiers,
                        blackFallenSoldiers: blackFallenSoldiers,
                        status: '',
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
    makeMove(row, col, source) {
        // console.log(Math.floor(source / 12), source % 12);
        // console.log(row, col);
        // console.log(this.state.gameID)
        let move = {
            gameID: this.state.gameID,
            userID: this.props.userID,
            initialRow: Math.floor(source / 12),
            initialCol: source % 12,
            afterRow: row,
            afterCol: col
        }
        let update = request(move, 'makeMove');
        update.then(value => {
            this.updateGame(value);
        });
    }

    isValidMove(row, col) {
        // console.log(row, col);
        // console.log(this.state.validMoves);
        for(let i = 0; i < this.state.validMoves.length; i++) {
            if(this.state.validMoves[i][0] === row && this.state.validMoves[i][1] === col)
                return true;
        }
        return false;
    }

    async getValidMoves(row, col) {
        let obj = {
            gameID: this.state.gameID,
            userID: this.props.userID,
            row: row,
            col: col
        };
        let update = request(obj,'getValidMovesSession');
        update.then((value => {
            this.setValidMoves(value); // TODO this is broken, below log works but line 186,187 are empty/are undefined
        }))

    }

    setValidMoves(value) {
        this.setState({
            validMoves: value
        })
        this.highlightValidMoves();
    }

    highlightValidMoves() {
        for(let i = 0; i < this.state.validMoves.length; i++) {
            if(document.getElementById(this.state.validMoves[i][0] + '-' + this.state.validMoves[i][1]).style.backgroundImage === "")
                document.getElementById(this.state.validMoves[i][0] + '-' + this.state.validMoves[i][1]).style.backgroundColor = "#00c4ffc9";
            else
                document.getElementById(this.state.validMoves[i][0] + '-' + this.state.validMoves[i][1]).style.backgroundColor = "#ff4936c9";
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

    getGamesDisplay(games) {
        const display = [];
        for(let i = 0; i < games.length; i++) {
            display.push(
                <button
                    key={"button" + games[i][0]}
                    className="game-btn"
                    id={games[i][0]}
                    onClick={() => this.getBoard(games[i][0])}>
                    {games[i][2]}
                </button>)
        }

        return (
            <div className="game-btn-container" >{display}</div>
        );
    }

    getBoard(gameID) {
        // console.log(gameID);
        let gameInfo = {
            gameID: gameID
        };
        let update = request(gameInfo, 'getBoard');
        update.then((value => {
            this.updateGame(value);
        }));
        this.setState({
            gameID: gameID
        });
    }

    render() {
        return (
            <Container>
                <Card>
                    <CardBody>
                        <div>
                            <h3>Games</h3>
                            <p>Games versus:</p>
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

