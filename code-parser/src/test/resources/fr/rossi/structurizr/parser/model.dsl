workspace {
    model {
        test = softwareSystem "Test"
        s = softwareSystem "System" {
            container "TestContainer" {
                service = component "Service" {
                    -> test "Test web service call" "HTTP"
                }
            }
        }
    }

    views {
        container s {
            include *
            autolayout lr
        }
    }
}