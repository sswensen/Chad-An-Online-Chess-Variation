import Bishop from '../pieces/bishop.js';
import King from '../pieces/king.js';
import Knight from '../pieces/knight.js';
import Pawn from '../pieces/pawn.js';
import Queen from '../pieces/queen.js';
import Rook from '../pieces/rook.js';

export default function initialiseChessBoard(){
  const squares = Array(144).fill(null);

  // 1 = White
  // 2 = Black

  // Black Pieces
    // Top row
  squares[33] = new Rook(2)
  squares[32] = new Rook(2)
  squares[31] = new Rook(2)
    // Middle row
  squares[45] = new Rook(2)
  squares[44] = new King(2)
  squares[43] = new Rook(2)
    // Bottom row
  squares[57] = new Rook(2)
  squares[56] = new Rook(2)
  squares[55] = new Rook(2)

  // White Pieces
    // Top row
  squares[110] = new Rook(1)
  squares[111] = new Rook(1)
  squares[112] = new Rook(1)
    // Middle row
  squares[98] = new Rook(1)
  squares[99] = new King(1)
  squares[100] = new Rook(1)
    // Bottom row
  squares[86] = new Rook(1)
  squares[87] = new Rook(1)
  squares[88] = new Rook(1)

  return squares;
}