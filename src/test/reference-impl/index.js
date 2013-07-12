"use strict";

var simplify = require('./lib/simplify.js'),
    points = require('./lib/test-data.js'),
    path = require('path'),
    fs = require('fs'),
    tolerances = [ 1.0, 1.5, 2.0, 4.0, 5.0 ];

function outputPoints(fileName, points) {
    var lines = [];
    lines = points.map(function (point) {
        return point.x + ',' + point.y;
    });
    fs.writeFileSync(path.join(__dirname, '..', 'resources', fileName), lines.join('\n'), 'utf-8');
}

outputPoints('points-all.txt', points);

tolerances.forEach(function (tolerance) {
    [ true, false ].forEach(function (highQuality) {
        var fileName = 'points-' + tolerance.toFixed(1) + '-' + (highQuality ? 'hq' : 'sq') + '.txt';
        outputPoints(fileName, simplify(points, tolerance, highQuality));
    })
});
