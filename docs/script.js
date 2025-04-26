// Feature data
const features = [
    {
        icon: "📊",
        title: "Real-Time Visualization",
        description: "Watch algorithms sort data in real-time with smooth animations that clearly illustrate each step of the process."
    },
    {
        icon: "🎮",
        title: "Interactive Controls",
        description: "Intuitive buttons to sort, reset, or learn about each algorithm. Visual indicators highlight the current elements being compared and swapped during sorting."
    },
    {
        icon: "📝",
        title: "Educational Resources",
        description: "Built-in explanations of each algorithm's time complexity and step-by-step breakdowns of how they work."
    },
    {
        icon: "🔧",
        title: "Customizable Input",
        description: "Test algorithms with your own datasets or generate random arrays to see how different inputs affect performance."
    },
    {
        icon: "📈",
        title: "Performance Metrics",
        description: "Compare algorithm efficiency with visual indicators of comparisons and swaps during execution."
    },
    {
        icon: "🔊",
        title: "Audio Explanations",
        description: "Built-in audio narration explains each algorithm's process as it runs (using FreeTTS technology)."
    }
];

// Algorithm data
const algorithms = [
    {
        name: "Insertion Sort",
        description: "Builds the final sorted array one item at a time, efficient for small data sets.",
        time: "O(n²)",
        space: "O(1)"
    },
    {
        name: "Quick Sort",
        description: "Divide-and-conquer algorithm that partitions the array around a pivot element.",
        time: "O(n log n)",
        space: "O(log n)"
    },
    {
        name: "Merge Sort",
        description: "Divides the array into halves, sorts them, and then merges the sorted halves.",
        time: "O(n log n)",
        space: "O(n)"
    },
    {
        name: "Bubble Sort",
        description: "Repeatedly steps through the list, compares adjacent elements and swaps them.",
        time: "O(n²)",
        space: "O(1)"
    }
];

// Populate features section
const featuresGrid = document.querySelector('.features-grid');
features.forEach(feature => {
    const featureCard = document.createElement('div');
    featureCard.className = 'feature-card';
    featureCard.innerHTML = `
        <div class="feature-icon">${feature.icon}</div>
        <h3>${feature.title}</h3>
        <p>${feature.description}</p>
    `;
    featuresGrid.appendChild(featureCard);
});

// Populate algorithms section
const algorithmsGrid = document.querySelector('.algorithms-grid');
algorithms.forEach(algorithm => {
    const algorithmCard = document.createElement('div');
    algorithmCard.className = 'algorithm-card';
    algorithmCard.innerHTML = `
        <div class="algorithm-img">${algorithm.name}</div>
        <div class="algorithm-content">
            <h3>${algorithm.name}</h3>
            <p>${algorithm.description}</p>
            <div class="algorithm-meta">
                <span>Time: ${algorithm.time}</span>
                <span>Space: ${algorithm.space}</span>
            </div>
        </div>
    `;
    algorithmsGrid.appendChild(algorithmCard);
});

// Smooth scrolling for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// Image gallery functionality (would be enhanced with a lightbox in production)
document.querySelectorAll('.screenshot-img').forEach(img => {
    img.addEventListener('click', function() {
        // In a production site, this would open a lightbox/modal
        console.log('Opening larger view of: ' + this.alt);
    });
});