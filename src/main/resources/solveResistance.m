function [R1, R2, R3,R4] = solveResistance(x1,x2,x3,x4)

r1=sym('r1');
r2=sym('r2');
r3=sym('r3');
r4=sym('r4');

[R1, R2, R3, R4]=solve(r1+1/(1/r2+1/r3+1/r4)==x1,r2+1/(1/r1+1/r3+1/r4)==x2,r3+1/(1/r2+1/r1+1/r4)==x3,r4+1/(1/r2+1/r3+1/r1)==x4);
R1 =double(R1);
R2 =double(R2);
R3 =double(R3);
R4 =double(R4);

end