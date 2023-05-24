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
    test_labels = ['10 conditions', '100 conditions', '1000 conditions']

    plt.boxplot(boxplot_values)
    plt.xlabel('Boxplot')
    plt.ylabel('Values')
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

boxplot__bench_values = [
    [12.43, 29.8945, 42.5365, 60.3585, 101.196],
    [86.467, 170.1205, 207.5785, 304.506, 500.361],
    [822.756, 1146.792, 1434.7285, 3121.4255, 5723.328],
]
plot_boxplot(boxplot__bench_values, "Time to execute benchmark code")

boxplot__xisnove_values = [
    [1718.088, 2552.9735, 4386.6265, 6942.0495, 13071.799],
    [7519.268, 9004.255, 10530.6745, 15649.065, 24680.205],
    [142068.556, 159536.908, 171053.904, 180661.0105, 207702.501],
]
plot_boxplot(boxplot__xisnove_values, "Time to instrument benchmark code")