### Problem 3: Theatre Seating

Note: Current implementation works with at least java 1.8 due to use of lambda

1. Run TheaterSeatingSimulator to read input and print output.
2. Theater layout and ticket requests should be separated by new line and finally ended by ```

	Sample input:
	6 6
	3 5 5 3
	4 6 6 4
	2 8 8 2
	6 6
	
	Smith 2
	Jones 5
	Davis 6
	Wilson 100
	Johnson 3
	Williams 4
	Brown 8
	Miller 12
	```
3. Output will be in the below format
	Sample output:
	
	Smith Row 1 Section 1
	Jones Row 2 Section 2
	Davis Row 1 Section 2
	Wilson Sorry, we can't handle your party.
	Johnson Row 2 Section 1
	Williams Row 1 Section 1
	Brown Row 4  Section 2
	Miller Call to split party.