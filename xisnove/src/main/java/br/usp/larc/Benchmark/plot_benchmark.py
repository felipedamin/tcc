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

boxplot__xisnove_values = [
    [1926.68, 2610.821, 4549.69, 7463.0375, 11873.14],
    [8156.445, 9385.9615, 12313.556, 16330.518, 26140.924],
    [133580.01, 158600.9985, 170710.7445, 187550.5635, 225210.686],
]
plot_boxplot(boxplot__xisnove_values, "Time to instrument benchmark code")

boxplot__bench_values = [
    [23.686, 28.7565, 58.0085, 74.4325, 141.858],
    [68.411, 108.819, 259.197, 324.6595, 532.026],
    [904.742, 1116.9885, 1232.807, 3260.4275, 6427.337],
]
plot_boxplot(boxplot__bench_values, "Time to execute benchmark code")

boxplot__instrumented_values = [
    [121.525, 336.5905, 550.4015, 968.4645, 1860.982],
    [807.38, 1170.101, 1663.473, 2158.2195, 3614.169],
    [5184.787, 7666.0495, 11701.9055, 15766.145, 21379.616],
]
plot_boxplot(boxplot__xisnove_values, "Time to execute instrumented benchmark code")
