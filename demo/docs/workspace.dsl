workspace {
    model {
        weatherAPI = softwareSystem "Weather API"
        s = softwareSystem "Demo" {
            database = container "Database"
            demoAPI = container "Demo API" {
                weatherService = component "Weather Service" {
                    -> weatherAPI "Get weather for a location" "HTTPS" "CHOPPER"
                }
            }
            demoAPI -> database
        }
    }

    views {
        container s {
            include *
            autolayout lr
        }
    }
}