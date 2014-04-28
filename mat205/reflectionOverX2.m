close all          			%close all figures
clear all               		%clear all old values
M=moviein(10);          		% set up the movie for 10 iterations (transformations)

x1=[0  2  3  0 ];         		%x1 coordinates of the vertices
x2=[0  5  2  0];           		%x2 coordinates of the vertices
plot(x1,x2);			%plot the ordered pairs (x1,x2)
axis([-5 5 -5 5])		%set the scale for the x1 and x2 axes
   		A=[-1 0; 0 1]; 		%standard matrix for the linear transformation

for i=1:5 			%for each iteration (transformation)
    						%transform the figure by transforming each vertex
     for j=1:4           		%for each vertex of the figure
        x(:,j)=A*[x1(j);x2(j)]; 	%transform the vertex into a new vertex
     end			%end the inner for loop
    
x1=x(1,:);         		 %label the x1 coordinates of the vector x
   			x2=x(2,:);          		%label the x2 coordinates of the vector x
  			plot(x1,x2);		%plot the ordered pairs (x1,x2)
			axis([-5 5 -5 5])	%set the scale for the x1 and x2 axes
M(:,i)=getframe;    	%create the movie
end 				%end the outer for loop

movie(M,1,0.5)          		% Play the movie 1 time with 1/2 a frame per second
                       					 % Play 1 time with 1 transformation every 2 seconds
