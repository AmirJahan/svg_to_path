package com.oddinstitute.temp

enum class PathType(val c: Char)
{
    Move('M'),
    Horizontal ('H'),
    Vertical ('V'),
    Line('L'),

    Curve('C'),
    SmoothCurve('S'), // cp1 is reflection

    Quad('Q'),
    SmoothQuad('T'), // cp 1 is reflection
}
