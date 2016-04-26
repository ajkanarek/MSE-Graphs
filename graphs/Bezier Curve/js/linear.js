


function addSeries(p0, p1, p2, p3, num_points) {
    // console.log("n: " + num_points);
    var chart = $("#container").highcharts();
    // console.log(chart);
    del = 1 / num_points;
    for (var i = 0; i <= num_points; ++i) {
        var t = i * del;
        // console.log("x: " + x + " y: " + y);
        var m0 = Math.pow((1-t), 3);
        var m1 = Math.pow((1-t), 2) * t * 3;
        var m2 = Math.pow(t, 2) * (1-t) * 3;
        var m3 = Math.pow(t, 3);
        var R = [0, 0];
        var x = (m0 * p0[0]) + (m1 * p1[0]) + (m2 * p2[0]) + (m3 * p3[0]);
        var y = (m0 * p0[1]) + (m1 * p1[1]) + (m2 * p2[1]) + (m3 * p3[1]);
        // console.log("x: " + x + " y: " + y);
        chart.series[2].addPoint([x, y]);
    }

    console.log("plot added");
}

// p0 = [0, 0]; p1 = [.15, .8]; p2 = [.7, .7]; p3 = [1, 1]; n = 200;