import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ZStack {
            Color(.bottomBar).ignoresSafeArea(.container, edges: .bottom)
            ComposeView()
                //.ignoresSafeArea(.keyboard) // Compose has own keyboard handler which doesn't work
        }
    }
}



