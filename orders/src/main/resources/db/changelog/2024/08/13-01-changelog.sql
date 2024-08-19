UPDATE orders
SET status = CASE
                 WHEN order_details = 'Order 1' THEN 'NEW'
                 WHEN order_details = 'Order 2' THEN 'AWAITING_PAYMENT'
                 WHEN order_details = 'Order 3' THEN 'PAYMENT_RECEIVED'
                 WHEN order_details = 'Order 4' THEN 'PROCESSING'
                 WHEN order_details = 'Order 5' THEN 'SHIPPED'
                 WHEN order_details = 'Order 6' THEN 'IN_TRANSIT'
                 WHEN order_details = 'Order 7' THEN 'DELIVERED'
                 WHEN order_details = 'Order 8' THEN 'CANCELLED'
                 WHEN order_details = 'Order 9' THEN 'RETURNED'
                 WHEN order_details = 'Order 10' THEN 'FAILED'
                 WHEN order_details = 'Order 11' THEN 'AWAITING_PAYMENT'
                 WHEN order_details = 'Order 12' THEN 'CANCELLED'
                 WHEN order_details = 'Order 13' THEN 'RETURNED'
                 WHEN order_details = 'Order 14' THEN 'PAYMENT_RECEIVED'
                 WHEN order_details = 'Order 15' THEN 'PROCESSING'
                 WHEN order_details = 'Order 16' THEN 'SHIPPED'
                 WHEN order_details = 'Order 17' THEN 'SHIPPED'
                 WHEN order_details = 'Order 18' THEN 'IN_TRANSIT'
                 WHEN order_details = 'Order 19' THEN 'IN_TRANSIT'
                 WHEN order_details = 'Order 20' THEN 'IN_TRANSIT'
    END
WHERE order_details IN ('Order 1', 'Order 2', 'Order 3', 'Order 4',
                        'Order 5', 'Order 6', 'Order 7', 'Order 8',
                        'Order 9', 'Order 10', 'Order 11', 'Order 12',
                        'Order 13', 'Order 14', 'Order 15', 'Order 16',
                        'Order 17', 'Order 18', 'Order 19', 'Order 20');