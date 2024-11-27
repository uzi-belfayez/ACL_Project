# Define the file path
input_file = "matrix.txt"  # Replace with your file name
output_file = "modified_matrix.txt"  # Output file to save the changes

# Open the file, read the content, and modify
with open(input_file, "r") as file:
    # Read the lines and split them into lists of integers
    matrix = [line.strip().split() for line in file.readlines()]

# Replace all occurrences of '0' with '14'
modified_matrix = [[str(16) if cell == '1' else cell for cell in row] for row in matrix]

# Write the modified matrix to a new file
with open(output_file, "w") as file:
    for row in modified_matrix:
        file.write(" ".join(row) + "\n")

print(f"Matrix has been modified and saved to {output_file}")
