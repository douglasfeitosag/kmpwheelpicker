#import "WheelPickerView.h"

@interface WheelPickerView () <UIPickerViewDelegate, UIPickerViewDataSource>

@property (nonatomic, strong) NSArray<NSString *> *options;
@property (nonatomic, assign) NSInteger selectedIndex;

@end

@implementation WheelPickerView

- (instancetype)initWithOptions:(NSArray<NSString *> *)options selectedIndex:(NSInteger)selectedIndex {
    self = [super initWithFrame:CGRectZero]; // Call super's designated initializer
    if (self) {
        _options = [options copy]; // Use copy for NSArray properties
        _selectedIndex = selectedIndex;

        self.delegate = self;
        self.dataSource = self;

        // Ensure selectedIndex is within bounds before selecting
        if (selectedIndex >= 0 && selectedIndex < _options.count) {
            [self selectRow:selectedIndex inComponent:0 animated:NO];
        } else if (_options.count > 0) {
            // Default to first item if selectedIndex is out of bounds but options exist
            _selectedIndex = 0;
            [self selectRow:0 inComponent:0 animated:NO];
        }


        self.translatesAutoresizingMaskIntoConstraints = NO;
    }
    return self;
}

// Since we marked initWithFrame and initWithCoder as unavailable in the .h,
// we don't strictly need to implement them to throw an error,
// but it's good practice if you want to be absolutely sure or provide a custom message.
// - (instancetype)initWithFrame:(CGRect)frame {
//     [NSException raise:NSInternalInconsistencyException format:@"-[%@ %@] cannot be called directly. Use initWithOptions:selectedIndex: instead.", NSStringFromClass([self class]), NSStringFromSelector(_cmd)];
//     return nil;
// }

// - (instancetype)initWithCoder:(NSCoder *)coder {
//     [NSException raise:NSInternalInconsistencyException format:@"-[%@ %@] cannot be called directly. Use initWithOptions:selectedIndex: instead.", NSStringFromClass([self class]), NSStringFromSelector(_cmd)];
//     return nil;
// }


- (void)didMoveToSuperview {
    [super didMoveToSuperview];
    [self config];
}

- (void)config {
    if (!self.superview) {
        return;
    }

    [NSLayoutConstraint activateConstraints:@[
            [self.centerXAnchor constraintEqualToAnchor:self.superview.centerXAnchor],
            [self.topAnchor constraintEqualToAnchor:self.superview.topAnchor],
            [self.bottomAnchor constraintEqualToAnchor:self.superview.bottomAnchor],
            // Note: In UIKit, UIPickerView has an intrinsic content size.
            // Forcing height and width like this can sometimes be problematic
            // if the intrinsic size is different, especially across iOS versions or device types.
            // Consider if these fixed dimensions are strictly necessary or if you can rely more
            // on intrinsic content size or more flexible layout constraints.
            [self.heightAnchor constraintEqualToConstant:216],
            [self.widthAnchor constraintEqualToConstant:320]
    ]];

    // Call delegate after constraints are set and view is in hierarchy
    if (self.pickerCallback && [self.pickerCallback respondsToSelector:@selector(didSelectWithIndex:)]) {
        // Ensure selectedIndex is valid before calling back
        if (self.selectedIndex >= 0 && self.selectedIndex < self.options.count) {
            [self.pickerCallback didSelectWithIndex:self.selectedIndex];
        } else if (self.options.count > 0) {
            // If selectedIndex was invalid but we defaulted to 0
            [self.pickerCallback didSelectWithIndex:0];
        }
    }
}

#pragma mark - UIPickerViewDataSource

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return self.options.count;
}

#pragma mark - UIPickerViewDelegate

- (nullable NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    if (row >= 0 && row < self.options.count) {
        return self.options[row];
    }
    return nil;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    if (self.pickerCallback && [self.pickerCallback respondsToSelector:@selector(didSelectWithIndex:)]) {
        if (row >= 0 && row < self.options.count) {
            self.selectedIndex = row; // Update internal selectedIndex
            [self.pickerCallback didSelectWithIndex:row];
        }
    }
}

@end