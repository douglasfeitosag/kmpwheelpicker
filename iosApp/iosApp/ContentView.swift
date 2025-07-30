import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = SharedEntryKt.MainViewController()

        if let windowScene = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .first,
           let statusBarFrame = windowScene.statusBarManager?.statusBarFrame,
           let window = windowScene.windows.first
        {
            let statusBarView = UIView(frame: statusBarFrame)
            statusBarView.backgroundColor = .clear
            window.addSubview(statusBarView)
        }

        if let navigationBar = viewController.navigationController?.navigationBar {
            navigationBar.setBackgroundImage(UIImage(), for: UIBarMetrics.default)
            navigationBar.shadowImage = UIImage()
            navigationBar.isTranslucent = true
        }
        viewController.navigationController?
            .interactivePopGestureRecognizer?
            .isEnabled = true

        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController,
                                context: Context) { /* no-op */ }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
            .background(Color.clear)
    }
}
