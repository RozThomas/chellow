def tariffs():
    return {
        '126': {
            'fixed-gbp-per-day': 0.0562,
            'day-gbp-per-kwh': 0.01645},
        '127,473': {
            'fixed-gbp-per-day': 0.0808,
            'day-gbp-per-kwh': 0.01645},
        '129': {
            'fixed-gbp-per-day': 0.0632,
            'day-gbp-per-kwh': 0.01737,
            'night-gbp-per-kwh': 0.00197},
        '130': {
            'fixed-gbp-per-day': 0.0758,
            'day-gbp-per-kwh': 0.01737,
            'night-gbp-per-kwh': 0.00197},
        '401,475': {
            'fixed-gbp-per-month': 18.58,
            'day-gbp-per-kwh': 0.00649,
            'night-gbp-per-kwh': 0.00126,
            'capacity-<=200-gbp-per-kva-per-month': 1.13,
            'capacity->200-gbp-per-kva-per-month': 1.00,
            'excess-gbp-per-kva-per-month': 0.45},
        '453,470': {
            'fixed-gbp-per-month': 15.97,
            'day-gbp-per-kwh': 0.00649,
            'night-gbp-per-kwh': 0.00126,
            'capacity-<=200-gbp-per-kva-per-month': 1.13,
            'capacity->200-gbp-per-kva-per-month': 1.00,
            'excess-gbp-per-kva-per-month': 0.45},
        '658,476': {
            'fixed-gbp-per-month': 105.18,
            'day-gbp-per-kwh': 0.00464,
            'night-gbp-per-kwh': 0.00116,
            'capacity-<=200-gbp-per-kva-per-month': 0.92,
            'capacity->200-gbp-per-kva-per-month': 0.92,
            'excess-gbp-per-kva-per-month': 0.43},
        '605': {
            'fixed-gbp-per-month': 107.80,
            'day-gbp-per-kwh':  0.00406,
            'capacity-<=200-gbp-per-kva-per-month': 0.92,
            'capacity->200-gbp-per-kva-per-month': 0.92,
            'excess-gbp-per-kva-per-month': 0.43},
        '450': {
            'fixed-gbp-per-month': 15.97,
            'day-gbp-per-kwh':   0.00544,
            'capacity-<=200-gbp-per-kva-per-month': 1.13,
            'capacity->200-gbp-per-kva-per-month': 1.00,
            'excess-gbp-per-kva-per-month': 0.45},
        '655': {
            'fixed-gbp-per-month': 105.18,
            'day-gbp-per-kwh': 0.00406,
            'capacity-<=200-gbp-per-kva-per-month': 0.92,
            'capacity->200-gbp-per-kva-per-month': 0.92,
            'excess-gbp-per-kva-per-month': 0.43},
        '909,477': {
            'gbp-per-kva-per-month': 0.51},
        '910,478': {
            'gbp-per-kva-per-month': 0.51}}


def lafs():
    return {
        'hv': {
            'peak': 1.043,
            'winter-weekday': 1.039,
            'other': 1.034,
            'night': 1.029},
        'lv': {
            'peak': 1.093,
            'winter-weekday': 1.087,
            'other': 1.078,
            'night': 1.074}}
