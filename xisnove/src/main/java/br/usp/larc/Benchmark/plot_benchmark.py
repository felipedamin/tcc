import matplotlib.pyplot as plt

def plot_average_and_std(average, std):
    tests = ['Benchmark10', 'Benchmark100', 'Benchmark1000']

    # Plot average values
    plt.plot(tests, average, label='Average')

    # Plot error bars for standard deviation
    plt.errorbar(tests, average, yerr=std, linestyle='', marker='o', label='Standard Deviation')

    # Add labels and title
    plt.xlabel('Tests')
    plt.ylabel('Time (ms)')
    plt.title('Average and Standard Deviation')

    # Set y-axis scale to logarithmic
    plt.yscale('log')

    # Add legend
    plt.legend()

    # Display the plot
    plt.show()

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

# average_values = [43.62499, 599.1202099999999, 4024.26116]
# std_values = [31.47488557929799, 3459.10735762732, 7291.703134192119]
# plot_average_and_std(average_values, std_values)

# average_values = [5697.72707, 15000.2861, 165555.54171000002]
# std_values = [15942.243554494098, 21706.97314614522, 59556.95933240389]
# plot_average_and_std(average_values, std_values)

boxplot_xisnove_values = [
    [1682.076, 2502.1685, 4023.6565, 5437.7245, 8043.97],
    [7906.338, 8992.945, 11400.0265, 15000.794, 21451.372],
    [127717.05, 144144.8525, 154919.138, 165136.3985, 188901.03],
]
plot_boxplot(boxplot_xisnove_values, "Time to instrument benchmark code")

boxplot_bench_values = [
    [22.672, 24.4595, 42.055, 71.021, 121.488],
    [101.22, 114.868, 119.4295, 132.056, 155.946],
    [848.839, 1086.496, 1155.626, 2191.853, 3330.476]
]
plot_boxplot(boxplot_bench_values, "Time to execute benchmark code")

boxplot_instrumented_values = [
    [50.944, 69.054, 84.2205, 199.5485, 379.407],
    [356.687, 598.3625, 637.8515, 784.0175, 1038.45],
    [2368.68, 2757.94, 3104.357, 6649.6345, 10814.553],
]

plot_boxplot(boxplot_instrumented_values, "Time to execute instrumented benchmark code")
