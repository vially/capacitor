import Foundation
import JavaScriptCore

@objc(CAPBackgroundTaskPlugin)
public class CAPBackgroundTaskPlugin : CAPPlugin {
  var tasks: [String:UIBackgroundTaskIdentifier] = [:]
  
  public override func load() {
    NotificationCenter.default.addObserver(self, selector: #selector(self.onAppTerminate), name: UIApplication.willTerminateNotification, object: nil)
    //NotificationCenter.default.addObserver(self, selector: #selector(self.onAppBackgrounded), name: NSNotification.Name.UIApplicationDidEnterBackground, object: nil)
  }
  
  @objc func beforeExit(_ call: CAPPluginCall) {
    var taskId: UIBackgroundTaskIdentifier = UIBackgroundTaskIdentifier.invalid
    
    taskId = UIApplication.shared.beginBackgroundTask {
      UIApplication.shared.endBackgroundTask(taskId)
      self.tasks[call.callbackId] = UIBackgroundTaskIdentifier.invalid
    }
    
    self.tasks[call.callbackId] = taskId
    
    call.success([
      "taskId": taskId
    ])
  }
  
  @objc func finish(_ call: CAPPluginCall) {
    guard let callbackTaskId = call.getString("taskId") else {
      call.error("Must provide taskId returned from calling start()")
      return
    }
    
    guard let taskId = tasks[callbackTaskId] else {
      call.error("No such task found")
      return
    }
    
    UIApplication.shared.endBackgroundTask(taskId)
  }

  
  @objc func fetch(_ call: CAPPluginCall) {
   
  }
  
  @objc func registerScript(_ call: CAPPluginCall) {
    guard let scriptFile = call.getString("file") else {
      call.error("Must provide script")
      return
    }
    print("Adding background script: " + scriptFile)
    
    let parts = scriptFile.split(separator: ".")
    let ext = String(parts.last!)
    let resourceName = parts[0...parts.count-2].joined() as String
    
    guard let scriptPath = Bundle.main.path(forResource: "public/" + resourceName, ofType: ext) else {
      print("Unable to read resource files.")
      call.error("Unable to load resource files")
      return
    }
    
    guard let scriptContents = try? String(contentsOfFile: scriptPath, encoding: .utf8) else {
      print("Unable to load script")
      call.error("Unable to load script")
      return
    }
    
    print("Loaded script contents:")
    print(scriptContents)
    
    let context = JSContext()
    let value = context?.evaluateScript(scriptContents)
    print("Evaluated script: ", value)
  }
  
  @objc func onAppTerminate() {
    print("APP TERMINATING IN BACKGROUND TASK")
  }
}



