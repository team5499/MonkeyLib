
package org.team5419.fault.auto

public class ParrallelRoutine(name: String, startPose: Pose2d, vararg routines: Routines): Routine(name, startPose, routines) {

  private val mActions: Array<out Routine>

  init {
      this.stepNumber = 0
      this.name = name
      this.startPose = startPose
      this.routines = routines.copyOf()

  }

  override public fun getCurrentAction(): Array<out Action> {
    val actions: MutableList<out Action> = MutableList()
    for(r: Routine in this.actions){
     actioins.add(r.getCurrentAction())
    }
    return r
  }

  override public fun lastStep(): Boolean {
    for(r: Routine in this.actions){
      if()
    }
  }

}
