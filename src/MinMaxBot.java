public class MinMaxBot extends Bot{
    private int maxDepth;
    private int MAX_INIT = -100;
    private int MIN_INIT = 100;


    public MinMaxBot(GameState state) {
        super(state);
    }
    
    public int[] move() {
        MinMaxIntermediate result = this.solve(true, this.MAX_INIT, this.MIN_INIT, maxDepth);
    }

    public MinMaxIntermediate solve(boolean isMaximizing, int alpha, int beta, int depth){//return x, y, and score
        if(this.state.isGameEnded() || depth==0){
            return new MinMaxIntermediate(this.state.getLastMove(), this.state.evaluate());
        }else{
            MinMaxIntermediate bestMove;
            if(isMaximizing){
                int maxval = this.MAX_INIT;
                int localAlpha = alpha;
                for (Coordinate move : state.getEmptyTile()) {
                    this.state.fill(move, this.state.getAIPiece());
                    MinMaxIntermediate result = this.solve(false, localAlpha, beta, depth-1);
                    this.state.reverse();
                    int newval = result.getVal();
                    if(newval>maxval){
                        bestMove = result;
                    }
                    if(newval>localAlpha){
                        localAlpha = newval;
                    }
                    if(localAlpha>beta){
                        //return dummy or result, whichever more efficient
                        return result;
                    }
                }
            }else{
                //minimizing
                int minvalue = this.MIN_INIT;
                int localBeta = beta;
                for (Coordinate move : this.state.getEmptyTile()){
                    this.state.fill(move, this.state.getPlayerPiece());
                    MinMaxIntermediate result = this.solve(true, alpha, localBeta, depth-1);
                    this.state.reverse();
                    int newValue = result.getVal();
                    if(newValue<minvalue){
                        bestMove = result;
                    }
                    if(newValue< localBeta){
                        localBeta = newValue;
                    }
                    if(alpha>localBeta){
                        return result;
                    }
                }
            }
            return bestMove;
        }
    }
}


//what happen if i store best move outside solve?
//if times out,



// def minMax(self, isMaximizing, alpha, beta):
// # Draw end
// if self.board.checkDraw() and self.board.checkWinner() == "-":
//     return 0, None
// # Winning end
// elif(self.board.checkWinner() == self.aiPiece):
//     return 100, None
// elif(self.board.checkWinner() == self.playerPiece):
//     return -100, None
// else:
//     if isMaximizing:
//         maxval = -999
        
//         for path in self.board.getEmptyTile():
//             self.board.fill(path[0],path[1], self.aiPiece)
//             val, _ = self.minMax(False, alpha, beta)
//             self.board.remove(path[0], path[1])
//             if val>maxval:
//                 maxval = val
//                 move = path
//             alpha = max(alpha, val)
//             if alpha>=beta:
//                 return maxval, move
//         return maxval, move
//     else:
//         minval = 999
//         for path in self.board.getEmptyTile():
//             self.board.fill(path[0],path[1], self.playerPiece)
//             val, _ = self.minMax(True,alpha,beta)
//             self.board.remove(path[0], path[1])
//             if val < minval:
//                 minval = val
//                 move = path
//             beta = min(beta, minval)
//             if alpha>=beta:
//                 return minval, move
//         return minval, move   