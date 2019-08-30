
package org.team5419.fault.auto

public class ParrallelRoutine(name: String, startPose: Pose2d, vararg routines: Routines) {
/*: Routine(name, startPose, routines) {*/

  private val mRoutines: Array<out Routine>
  var stepNumber: Int
      get() = field
  val name: String
      get() = field
  val startPose: Pose2d
      get() = field

  @Suppress("SpreadOperator")
  public constructor(name: String, vararg actions: Action) :
      this(name, Pose2d(Vector2(0, 0), Rotation2d.fromDegrees(0.0)), *actions)

  //inheriet init method
  init {
      this.stepNumber = 0
      this.name = name
      this.startPose = startPose
      this.routines = routines.copyOf()
  }

  override public fun getCurrentAction(): Array<out Action> {
    val actions: MutableList<out Action> = MutableList()
    for(r: Routine in this.actions){
     actions.add(r.getCurrentAction())
    }
    return r
  }

  override public fun advanceRoutine() {
    if(!this.isLastStep()){
      ste
      for(r: Routine in this.routines){
        r.advanceRoutine()
      }
    }
  }

  override public fun isLastStep(): Boolean {
    for(r: Routine in this.actions){
      if(!r.lastStep()){
        return false
      }
    }
    return true
  }

  override public fun rest() {
    this.stepNumber = 0
    for(r: Routine in this.routines){
      r.reset()
    }
  }

}
