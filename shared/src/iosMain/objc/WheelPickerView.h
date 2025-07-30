#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

// Forward declaration of the protocol
@protocol WheelPickerDelegate;

@interface WheelPickerView : UIPickerView

@property (nonatomic, weak, nullable) id<WheelPickerDelegate> pickerCallback;

// Designated initializer
- (instancetype)initWithOptions:(NSArray<NSString *> *)options selectedIndex:(NSInteger)selectedIndex NS_DESIGNATED_INITIALIZER;

// Make unavailable initializers explicit
- (instancetype)initWithFrame:(CGRect)frame NS_UNAVAILABLE;
- (instancetype)initWithCoder:(NSCoder *)coder NS_UNAVAILABLE;

@end

// Protocol definition
@protocol WheelPickerDelegate <NSObject>
- (void)didSelectWithIndex:(NSInteger)index;
@end

NS_ASSUME_NONNULL_END