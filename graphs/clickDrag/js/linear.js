/** Solve a linear system of equations given by a n&times;n matrix
    with a result vector n&times;1. */
function gauss(A) {
    var n = A.length;

    for (var i=0; i<n; i++) {
        // Search for maximum in this column
        var maxEl = Math.abs(A[i][i]);
        var maxRow = i;
        for(var k=i+1; k<n; k++) {
            if (Math.abs(A[k][i]) > maxEl) {
                maxEl = Math.abs(A[k][i]);
                maxRow = k;
            }
        }

        // Swap maximum row with current row (column by column)
        for (var k=i; k<n+1; k++) {
            var tmp = A[maxRow][k];
            A[maxRow][k] = A[i][k];
            A[i][k] = tmp;
        }

        // Make all rows below this one 0 in current column
        for (k=i+1; k<n; k++) {
            var c = -A[k][i]/A[i][i];
            for(var j=i; j<n+1; j++) {
                if (i==j) {
                    A[k][j] = 0;
                } else {
                    A[k][j] += c * A[i][j];
                }
            }
        }
    }

    // Solve equation Ax=b for an upper triangular matrix A
    var x = new Array(n);
    for (var i=n-1; i>-1; i--) {
        x[i] = A[i][n]/A[i][i];
        for (var k=i-1; k>-1; k--) {
            A[k][n] -= A[k][i] * x[i];
        }
    }
    return x;
}

function getMatrix(xi, f) {
    xiCube = Math.pow(xi, 3);
    xiSquare = Math.pow(xi, 2);
    //    a  b  c  d  0  y
    A = [[0, 0, 0, 1, 0],              // y = 0 at x = 0
         [1, 1, 1, 1, 1],              // y = 1 at x = 1
         [xiCube, xiSquare, xi, 1, f], // y = f at x = xi
         [3, 2, 1, 0, 1]               // y' = 1 at x = 1
        ] 
    return A

}

var Fraction = algebra.Fraction;
var Expression = algebra.Expression;
var Equation = algebra.Equation;

// given: x, xi and f coordinates, and list of consts [a, b, c, d]
// results in y value
function getY(x, xi, f, consts) {
    if (x == 0) {
        return 0;
    }
    if (x == 1) {
        return 1;
    }
    if (x == xi) {
        return f;
    }
    var a = consts[0];
    var b = consts[1];
    var c = consts[2];
    var d = consts[3];
    var y = (Math.pow(x, 3) * a) + (Math.pow(x, 2) * b) + (x * c) + d
    console.log("a: " + a + " b: " + b + " c: " + c + " d: " + d);
    return y;
}





















