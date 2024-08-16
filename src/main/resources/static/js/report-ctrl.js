approute.controller('report-ctrl', function ($scope, $http) {
    $http.get("/api/report/data").then(function (response) {
        let data = response.data;

        // Extract data for charts
        let populationData = data.data;
        let cityNames = data.labels;
        let totalPro = data.totalpro;
        let totalCate = data.totalcate;

        // Update charts with fetched data
        let myChart = document.getElementById('myChart').getContext('2d');
        new Chart(myChart, {
            type: 'bar',
            data: {
                labels: cityNames,
                datasets: [{
                    label: 'Amounts ($)',
                    data: populationData,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)',
                        'rgba(255, 159, 64, 0.6)',
                        'rgba(255, 99, 132, 0.6)'
                    ],
                    borderWidth: 1,
                    borderColor: '#777',
                    hoverBorderWidth: 3,
                    hoverBorderColor: 'white'
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'REPORT ORDER',
                    fontSize: 25
                },
                legend: {
                    display: true,
                    position: 'right',
                    labels: {
                        fontColor: 'red'
                    }
                },
                tooltips: {
                    enabled: true
                }
            }
        });

        let total = document.getElementById('total').getContext('2d');
        new Chart(total, {
            type: 'pie',
            data: {
                labels: ['Total Products', 'Total Categories'],
                datasets: [{
                    label: 'Count',
                    data: [totalPro, totalCate],
                    backgroundColor: [
                        'cyan', 'gray'
                    ],
                    borderWidth: 1,
                    borderColor: '#777',
                    hoverBorderWidth: 3,
                    hoverBorderColor: 'white'
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'REPORT PRODUCT',
                    fontSize: 25
                },
                legend: {
                    display: true,
                    position: 'right',
                    labels: {
                        fontColor: 'black'
                    }
                },
                tooltips: {
                    enabled: true
                }
            }
        });
    });
});
