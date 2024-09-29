import matplotlib.pyplot as plt

def plot_boxplot(boxplot_values, title):
    test_labels = ['10', '100', '1000']

    plt.boxplot(boxplot_values)
    plt.xlabel('Number of Conditions')
    plt.ylabel('Time (ms)')
    plt.title(title)
    plt.yscale('log')
    
    # Customize legend labels on the x-axis
    plt.xticks(range(1, len(test_labels) + 1), test_labels)

    plt.show()

boxplot_xisnove_values = [
    [1682.076, 2502.1685, 4023.6565, 5437.7245, 8043.97],
    [7906.338, 8992.945, 11400.0265, 15000.794, 21451.372],
    [127717.05, 144144.8525, 154919.138, 165136.3985, 188901.03],
]
plot_boxplot(boxplot_xisnove_values, "Time to instrument benchmark code")

boxplot_bench_values = [
    [22.672, 24.4595, 42.055, 71.021, 121.488],
    [89.282, 132.988, 190.56, 234.4625, 375.628],
    [848.839, 1086.496, 1155.626, 2191.853, 3330.476]
]

boxplot_instrumented_values = [
    [50.944, 69.054, 84.2205, 199.5485, 379.407],
    [240.847, 444.125, 575.874, 1248.532, 2142.094],
    [2368.68, 2757.94, 3104.357, 6649.6345, 10814.553],
]

boxplot_comparison = []
for x in range(len(boxplot_bench_values)):
    boxplot_comparison.append(boxplot_bench_values[x])
    boxplot_comparison.append(boxplot_instrumented_values[x])
positions = [1,2,4,5,7,8]
labels = ['10', '10', '100', '100', '1000', '1000']

# Plot the boxplots
boxplot = plt.boxplot(boxplot_comparison, positions=positions, labels=labels, patch_artist=True)

# Assign colors to odd and even boxplots
for i in range(len(boxplot['boxes'])):
    if i % 2 == 0:
        boxplot['boxes'][i].set_facecolor("blue")
    else:
        boxplot['boxes'][i].set_facecolor("red")

# Create a legend for the color scheme
legend_elements = [
    plt.Line2D([0], [0], color="blue", lw=4, label='Time to execute benchmark code'),
    plt.Line2D([0], [0], color="red", lw=4, label='Time to execute instrumented benchmark code')
]
plt.legend(handles=legend_elements)

# Customize the plot
plt.xlabel('Number of Conditions')
plt.ylabel('Time (ms)')
plt.title('Runtime comparison')
plt.yscale('log')

# Show the plot
plt.show()